from django.template import RequestContext, loader
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from django.contrib.auth.models import User
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from rango.models import Category, Page, CategoryForm, PageForm, UserProfile, UserForm, UserProfileForm

def index(request):
    template = loader.get_template('rango/index.html')
    
    cat_list = Category.objects.all()
    for cat in cat_list:
        category_name = cat.name
        cat.url = encode_category(category_name)
    
    context = RequestContext(request, {'cat_list': cat_list})
    return HttpResponse(template.render(context))

def category(request, category_name_url):
    template = loader.get_template('rango/category.html')
    
    category_name = decode_category(category_name_url)
    context_dict = {'category_name_url' : category_name_url,
                    'category_name' : category_name}
    
    cat = Category.objects.filter(name=category_name)

    if cat:
        pages = Page.objects.filter(category=cat)
        context_dict['pages']=pages
        
    context = RequestContext(request,context_dict)
    return HttpResponse(template.render(context))

def add_category(request):
        # immediately get the context - as it may contain posting data
        context = RequestContext(request)
        if request.method == 'POST':
                # data has been entered into the form via Post
                form = CategoryForm(request.POST)
                if form.is_valid():
                        # the form has been correctly filled in,
                        # so lets save the data to the model
                        cat = form.save(commit=True)
                        # show the index page with the list of categories
                        return index(request)
                else:
                        # the form contains errors,
                        # show the form again, with error messages
                        pass
        else:
                # a GET request was made, so we simply show a blank/empty form.
                form = CategoryForm()

        # pass on the context, and the form data.
        return render_to_response('rango/add_category.html',
                {'form': form }, context)

def add_page(request, category_name_url):
        context = RequestContext(request)

        category_name = decode_category(category_name_url)
        if request.method == 'POST':
                form = PageForm(request.POST)
                if form.is_valid():
                        # this time we cant commit straight away
                        # because not all fields are populated
                        page = form.save(commit=False)
                        # retrieve and assign the category object to the new page
                        cat = Category.objects.get(name=category_name)
                        page.category = cat
                        # also plug in a default value for the no. of page views
                        page.views = 0
                        page.save()
                        # Now that the page is saved, display the category instead.
                        return category(request, category_name)
                else:
                        print form.errors
        else:
                form = PageForm()

        return render_to_response( 'rango/add_page.html',
                        {'category_name_url': category_name_url,
                                'category_name': category_name, 'form': form },
                        context)

def encode_category(category_name):
        # returns the name converted for insert into url
        return category_name.replace(' ','_')

def decode_category(category_url):
        # returns the category name given the category url portion
        return category_url.replace('_',' ')

def register(request):
    context = RequestContext(request)
    registered = False
    if request.method == 'POST':
        uform = UserForm(data = request.POST)
        pform = UserProfileForm(data = request.POST)
        if uform.is_valid() and pform.is_valid():
            user = uform.save(commit = False)
            user.set_password(user.password)
            user.save()
            profile = pform.save(commit = False)
            profile.user = user
            profile.save()
            registered = True
        else:
            print uform.errors, pform.errors
    else:
        uform = UserForm()
        pform = UserProfileForm()
    
    return render_to_response('rango/register.html',{'uform':uform,'pform':pform,'registered':registered}, context)


def user_login(request):
    context = RequestContext(request)
    if request.method == 'POST':
          username = request.POST['username']
          password = request.POST['password']
          user = authenticate(username=username, password=password)
          if user is not None:
              if user.is_active:
                  login(request, user)
                  # Redirect to index page.
                  return HttpResponseRedirect('/rango')
              else:
                  # Return a 'disabled account' error message
                  return HttpResponse("You're account is disabled.")
          else:
              # Return an 'invalid login' error message.
              print  "invalid login details " + username + " " + password
              return render_to_response('rango/login.html', {}, context)
    else:
        # the login is a  GET request, so just show the user the login form.
        return render_to_response('rango/login.html', {}, context)

@login_required
def restricted(request):
    return HttpResponse('YOU CAN SEE THIS BIT')

@login_required
def user_logout(request):
    context = RequestContext(request)
    logout(request)
    # Redirect back to index page.
    return HttpResponseRedirect('/rango/')
